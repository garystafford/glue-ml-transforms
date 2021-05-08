# Creating a Machine Learning Transform with AWS Glue

Notes and code for the
AWS [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html).

## Reference

- [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
- [Tuning Machine Learning Transforms in AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/add-job-machine-learning-transform-tuning.html)
- [Developing and Testing ETL Scripts Locally Using the AWS Glue ETL Library](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html)

## Process

1. Create CloudFormation Stack
2. Teach transform
3. Uploads labels
4. Estimate transform quality
4. ...

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