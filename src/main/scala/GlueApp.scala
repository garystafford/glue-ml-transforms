import com.amazonaws.services.glue.ChoiceOption
import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.ml.FindMatches // won't import locally - local development restriction
import com.amazonaws.services.glue.util.GlueArgParser
import com.amazonaws.services.glue.util.Job
import com.amazonaws.services.glue.util.JsonOptions
import org.apache.spark.SparkContext

import scala.collection.JavaConverters._

object GlueApp {
  // ****** CHANGE ME ******
  var mlTransformId: String = "tfm-behuhzsrsnpm3vk8tx2sehmh9owuh8lw88jeuwdz"
  var resultBucket: String = "s3://glue-ml-transform-111222333444-us-east-1"

  // ****** OPTIONAL CHANGE ME ******
  val glueDatabase: String = "demo-db-dblp-acm"
  val glueTable: String = "dblp_acm_records_csv"

  def main(sysArgs: Array[String]) {
    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)
    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("JOB_NAME").toArray)

    Job.init(args("JOB_NAME"), glueContext, args.asJava)

    val datasource = glueContext.getCatalogSource(
      database = glueDatabase,
      tableName = glueTable,
      redshiftTmpDir = "",
      transformationContext = "datasource"
    ).getDynamicFrame()

    val resolvechoice = datasource.resolveChoice(
      choiceOption = Some(ChoiceOption("MATCH_CATALOG")),
      database = Some(glueDatabase),
      tableName = Some(glueTable),
      transformationContext = "resolvechoice"
    )

    // FindMatches is a local development restriction but will work fine when run as Glue Job
    val findmatches = FindMatches.apply(
      frame = resolvechoice,
      transformId = mlTransformId,
      transformationContext = "findmatches")

    val singlePartition = findmatches.coalesce(1)

    val datasink = glueContext.getSinkWithFormat(
      connectionType = "s3",
      options = JsonOptions(s"""{"path": "$resultBucket"}"""),
      transformationContext = "datasink",
      format = "csv"
    ).writeDynamicFrame(singlePartition)
    Job.commit()
  }
}