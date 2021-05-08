# Creating a Machine Learning Transform with AWS Glue

Notes and code for the AWS [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html).

## Reference

- [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
- [Tuning Machine Learning Transforms in AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/add-job-machine-learning-transform-tuning.html)
- [Developing and Testing ETL Scripts Locally Using the AWS Glue ETL Library](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html)

## Process

1. Create CloudFormation Stack
2. Modify Scala Glue script's `transformId` value and bucket location in `datasink` section
3. Upload Scala Glue script to new S3 bucket
4. Run Teach transform
5. Uploads labels
6. Estimate transform quality
7. Run Glue Job containing uploaded Scala Glue script

## Commands

### Optional: Retrieve Datasets from S3.

```shell
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/records/dblp_acm_records.csv dblp_acm_records.csv
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv dblp_acm_labels.csv
```

### Create CloudFormation Stack

```
aws cloudformation create-stack \
    --stack-name glue-ml-transform \
    --template-body file://cfn-templates/resources.yaml \
    --parameters \
        ParameterKey=GlueServiceRole,ParameterValue=AWSGlueServiceRole-ml-demo \
    --capabilities CAPABILITY_NAMED_IAM
```

## Labeled Dataset Location

Change Region to match your Region.

```txt
s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv
```

### Copy Script to S3

Change S3 bucket name.

```shell
aws s3 cp ./src/main/scala/GlueScript.scala \
  s3://glue-ml-transform-results-111222333444-us-east-1/demo-etl-dblp-acm
```