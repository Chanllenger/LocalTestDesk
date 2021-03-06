package com.zongteng.ztetl.util.ods.script

import java.io.{File, FileOutputStream}
import java.nio.ByteBuffer

import com.zongteng.ztetl.common.SystemCodeUtil
import com.zongteng.ztetl.entity.common.AssistParameter
import com.zongteng.ztetl.util.Theme
import com.zongteng.ztetl.util.stream.AssistParameterUtil.check

import scala.io.Source

/**
  * 通过路径判断文件存不存在；如果存在，采取追加的策略；如果不存在，生成文件将数据写进去。
  */
object AssistParameterUtilScript {

  var recordNum = 0

  def outPutAssistParameterUtilScript(params: AssistParameter, theme: String) = {

    // 获取caseClass的路径
    val caseClassPath: String = getCaseClassPath(theme)

    val file = new File(caseClassPath)
    var scripts: List[String] = null
    if (!file.exists) { // 新建
      scripts = getNewFileScript(params, theme)
    }  else { // 追加
      val beforeUpdateText: String = Source.fromFile(caseClassPath, "utf8").mkString
      scripts = getFileAppaenScript(params, beforeUpdateText)
    }

    val f = new FileOutputStream(caseClassPath).getChannel
    f.write(ByteBuffer.wrap(scripts.mkString("").getBytes))
    f.close

    recordNum += 1
  }

  /**
    * 文件已经存在，覆盖文件需要的脚本
    * @param params
    * @param beforeUpdateText
    */
  def getFileAppaenScript(params: AssistParameter, beforeUpdateText: String) = {

    val database: String = params.database
    val table: String = params.table

    val caseScript = s" case ${addDoubleQuotes(database + "." + table)} => $database" + "$" + s"$table(database, table)\n"

    val defScript: String = getMysqlDataBase$MysqlTableScript(params)

    // 添加一个case方法
    val index: Int = beforeUpdateText.indexOf("case _")
    val beforeUpdateText2: String = beforeUpdateText.substring(0, index) + (caseScript) + "     " + beforeUpdateText.substring(index)

    // 添加一个def 方法
    val lastIndex: Int = beforeUpdateText2.lastIndexOf("}")
    val afterUpdateText: String = beforeUpdateText2.substring(0, lastIndex) + "\n" + defScript + "}"

    List(afterUpdateText)
  }

  /**
    * 文件不存在，创建新的文件需要的脚本
    * @param params
    * @param theme
    * @return
    */
  def getNewFileScript(params: AssistParameter, theme: String) = {

    val packageScript = "package com.zongteng.ztetl.util.stream\n\n"
    val importScript = "import org.apache.commons.lang3.StringUtils\n"
    val importScript2 = "import com.zongteng.ztetl.entity.common.AssistParameter\n\n"
    val scriptHead = s"object ${theme}AssistParameterUtil {\n\n"
    val method1: Array[String] = getEntityObjectScriptCommon(params.database, params.table)
    val method2: String = getMysqlDataBase$MysqlTableScript(params) + "\n"
    val method3: String = getCheckScript()
    val scriptTail = "\n}"

    val list: List[String] = packageScript :: importScript :: importScript2 :: scriptHead :: method1.toList ::: method2 :: method3 :: scriptTail :: Nil

    list
  }


  def getEntityObjectScriptCommon(mysqlDataBase: String, mysqlTable: String) = {

    val database = mysqlDataBase
    val table = mysqlTable

    val scriptHead =
      s"  def getEntityObject(database: String, table: String) = { \n\n" +
      s"    assert(StringUtils.isNotBlank(database), ${addDoubleQuotes("数据库不能为空")})\n" +
      s"    assert(StringUtils.isNotBlank(table), ${addDoubleQuotes("表名不能为空")})\n\n" +
      s"    val dt: String = database + ${addDoubleQuotes(".")} + table \n\n" +
      s"    dt match {\n"

    val caseScript = s"      case ${addDoubleQuotes(database + "." + table)} => $database" + "$" + s"$table(database, table)\n"

    val caseScript2 = s"     case _ => throw new Exception(${addDoubleQuotes("数据库 {")} + database + ${addDoubleQuotes("} 中表{")} + table + ${addDoubleQuotes("} 对应的方法不存在,请添加")})\n"

    val scriptEnd = "    }\n  }\n\n"

    Array(scriptHead, caseScript, caseScript2, scriptEnd)
  }

  def getCheckScript() = {
    "  def check(assistParameter: AssistParameter) = {\n" +
      "    assert(assistParameter.className != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的样例类不存在,请添加\")\n" +
      "    assert(assistParameter.primaryKey != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的主键不存在,请添加\")\n" +
      "    assert(assistParameter.cf != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的列族不存在,请添加\")\n" +
      "    assert(assistParameter.datasource_num_id != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的数据来源不存在,请添加\")\n" +
      "    assert(assistParameter.add_time_field != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的新增时间不存在,请添加\")\n" +
      "    assert(assistParameter.update_time_field_1 != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的修改时间1_时间戳不存在,请添加\")\n" +
      "    assert(assistParameter.update_time_field_2 != null, \"数据库 {\" + assistParameter.database + \"} 中表{\" + assistParameter.table + \"} 对应的修改时间2_修改时间不存在,请添加\")\n" +
      "    assistParameter\n  " +
      "}"
  }

  def getMysqlDataBase$MysqlTableScript(params: AssistParameter) = {

    val mysqlDataBase = params.database
    val mysqlTable = params.table
    val clazz = params.className.getName
    val primaryKey = params.primaryKey
    val cf = params.cf
    val datasource_num_id = params.datasource_num_id
    val add_time_field = params.add_time_field
    val update_time_field_1 = params.update_time_field_1
    val update_time_field_2 = params.update_time_field_2

    val defScript = s"  def ${mysqlDataBase + "$" + mysqlTable}(database: String, table: String): AssistParameter = {\n" +
      s"    val clazz: Class[_] = classOf[$clazz]\n" +
      s"    val primaryKey: String = ${addDoubleQuotes(primaryKey)}\n" +
      s"    val cf: String = ${addDoubleQuotes(cf)}\n" +
      s"    val datasource_num_id: String = ${addDoubleQuotes(datasource_num_id)}\n" +
      s"    val add_time_field = ${addDoubleQuotes(add_time_field)}\n" +
      s"    val update_time_field_1 = ${addDoubleQuotes(update_time_field_1)}\n" +
      s"    val update_time_field_2 = ${addDoubleQuotes(update_time_field_2)}\n" +
      s"    check(AssistParameter(clazz, primaryKey, cf, datasource_num_id, add_time_field, update_time_field_1, update_time_field_2, database, table))\n" +
      s"  }\n"

    defScript
  }

  /**
    * 获取AssistParameterUtil.scala的路径
    * @param theme 主题
    * @return
    */
  def getCaseClassPath(theme: String) = {
    val classFile: String = "com\\zongteng\\ztetl\\util\\stream"
    val path: String = getClass.getClassLoader.getResource(classFile).getPath
    val caseClassPath: String = path.replace("%5c", "/").
      replace("target/classes", "src\\main\\scala") + s"\\" + s"${theme}AssistParameterUtil.scala"

    println(caseClassPath)

    caseClassPath
  }

  /**
    * 字符串左右添加双引号
    * @param str
    * @return
    */
  def addDoubleQuotes(str: String) = {
    "\"" + str + "\""
  }

}
