import com.amazonaws.services.glue.ChoiceOption
import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.ResolveSpec
import com.amazonaws.services.glue.errors.CallSite
import com.amazonaws.services.glue.ml.FindMatches
import com.amazonaws.services.glue.util.GlueArgParser
import com.amazonaws.services.glue.util.Job
import com.amazonaws.services.glue.util.JsonOptions
import org.apache.spark.SparkContext

import scala.collection.JavaConverters._

object GlueApp {
  def main(sysArgs: Array[String]) {
    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)
    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("JOB_NAME").toArray)

    Job.init(args("JOB_NAME"), glueContext, args.asJava)

    val datasource0 = glueContext.getCatalogSource(
      database = "demo-db-dblp-acm",
      tableName = "dblp_acm_records_csv",
      redshiftTmpDir = "",
      transformationContext = "datasource0"
    ).getDynamicFrame()

    val resolvechoice1 = datasource0.resolveChoice(
      choiceOption = Some(ChoiceOption("MATCH_CATALOG")),
      database = Some("demo-db-dblp-acm"),
      tableName = Some("dblp_acm_records_csv"),
      transformationContext = "resolvechoice1"
    )

    val findmatches2 = FindMatches.apply(
      frame = resolvechoice1,
      transformId = "tfm-9758e521c94afe2c93e3d58bb995618a7f2d9d5f",
      transformationContext = "findmatches2")

    val single_partition = findmatches2.repartition(1)

    val datasink3 = glueContext.getSinkWithFormat(
      connectionType = "s3",
      options = JsonOptions("""{"path": "s3://demo-lab-05591e78-c7a7-11ea-9e7c-1f5912642e39"}"""),
      transformationContext = "datasink3",
      format = "csv"
    ).writeDynamicFrame(single_partition)
    Job.commit()
  }
}