# Creating a Machine Learning Transform with AWS Glue

Notes and source code for the
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

In reference to step #3, above. Change S3 bucket name to match your environment.

```shell
export BUCKET_NAME="glue-ml-transform-results-111222333444-us-east-1"

aws s3 cp ./src/main/scala/GlueApp.scala \
  s3://$BUCKET_NAME/demo-etl-dblp-acm
```

## Labeled Dataset Location

In reference to step #5, above.

```txt
s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv
```

## Optional: Retrieve Public Datasets from S3.

Retrieve original public dataset and labels from S3.

```shell
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/records/dblp_acm_records.csv dblp_acm_records.csv
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv dblp_acm_labels.csv
```

## Reference

- [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
- [Tuning Machine Learning Transforms in AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/add-job-machine-learning-transform-tuning.html)
- [Developing and Testing ETL Scripts Locally Using the AWS Glue ETL Library](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html)
