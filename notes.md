# Creating a Machine Learning Transform with AWS Glue

Notes and code for the AWS [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html).

## Reference
- [Tutorial: Creating a Machine Learning Transform with AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/machine-learning-transform-tutorial.html)
- [Tuning Machine Learning Transforms in AWS Glue](https://docs.aws.amazon.com/glue/latest/dg/add-job-machine-learning-transform-tuning.html)
- [Developing and Testing ETL Scripts Locally Using the AWS Glue ETL Library](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html)

# Commands

Retrieve datasets from S3.

```shell
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/records/dblp_acm_records.csv dblp_acm_records.csv
aws s3 cp s3://ml-transforms-public-datasets-us-east-1/dblp-acm/labels/dblp_acm_labels.csv dblp_acm_labels.csv
```