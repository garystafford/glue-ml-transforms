# Creating a Machine Learning Transform with AWS Glue

Source code and notes for a quick start version of the
AWS [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
.

## Workflow with this project

1. Create the CloudFormation Stack
2. Modify Scala Glue script's `mlTransformId` and `resultBucket` vars to match your environment
3. Upload Scala Glue script to new S3 bucket
4. Run 'Teach transform'
5. Run 'Uploads labels'
6. Run 'Estimate transform quality'
7. Run the Glue Job containing uploaded Scala Glue script

Running the Glue Crawler, included in the CloudFormation Stack, is not required for the tutorial.

Note Glue 2.0 does not support `FindMatches` machine learning transforms. You must use Glue 1.0 for Glue Job.

## Create CloudFormation Stack

In reference to step #1, above.

```
aws cloudformation create-stack \
    --stack-name glue-ml-transform \
    --template-body file://cfn-templates/resources.yaml \
    --parameters \
        ParameterKey=GlueServiceRole,ParameterValue=AWSGlueServiceRole-ml-demo \
    --capabilities CAPABILITY_NAMED_IAM
```

## Copy Script to S3

In reference to step #3, above. Change the S3 bucket name to match your environment.

```shell
export YOUR_BUCKET_NAME="glue-ml-transform-results-111222333444-us-east-1"

aws s3 cp ./src/main/scala/GlueApp.scala \
  s3://$YOUR_BUCKET_NAME/demo-etl-dblp-acm
```

## Labeled Dataset Location

In reference to step #5, above.

```txt
s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv
```

## Optional: Retrieve Public Datasets

Retrieve original public dataset and labels from S3.

```shell
export SOURCE_BUCKET_NAME="ml-transforms-public-datasets-us-east-1"

aws s3 cp s3://$SOURCE_BUCKET_NAME/dblp-acm/records/dblp_acm_records.csv \
  dblp_acm_records.csv

aws s3 cp s3://$SOURCE_BUCKET_NAME/dblp-acm/labels/dblp_acm_labels.csv \
  dblp_acm_labels.csv
```

## Reference

- [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
- [Tuning Machine Learning Transforms in AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/add-job-machine-learning-transform-tuning.html)
- [Developing and Testing ETL Scripts Locally Using the AWS Glue ETL Library](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html)
- [AWS Glue: Local Development Restrictions](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html#local-dev-restrictions)
- [Glue 2.0 Features Not Supported: FindMatches machine learning transforms](https://github.com/awsdocs/aws-glue-developer-guide/blob/master/doc_source/reduced-start-times-spark-etl-jobs.md#features-not-supported)

---

<i>The contents of this repository represent my viewpoints and not of my past or current employers, including Amazon Web Services (AWS). All third-party libraries, modules, plugins, and SDKs are the property of their respective owners.</i>
