# Simple S3

This library provides classes to make interacting with S3 buckets as simple
as possible, as well as providing classes to make it easier to test code
that interacts with S3 buckets. It is also possible to use a class which logs
all interactions with S3 in a json format for consumption by tools such as
Elastic Search and Kibana.

## Usage

To use the library from a program you will need to add a dependency to your project. In
gradle you would do this by adding the following to your build.gradle file:

```
dependencies {
    compile 'com.github.michaelruocco:simple-s3:1.0.0'
}
```

### Basic Usage

You can create an instance of the DefaultSimpleS3 class and use it to read
file content, either as a String or InputStream. It also allow writing content
into a bucket as a String and deleting objects from a bucket.

```
ObjectDefinition definition = new ObjectDefinition("my-bucket", "my-key");
SimpleS3 s3 = new DefaultSimpleS3(AmazonS3ClientBuilder.standard().build());

String readContent = s3.getObjectAsString(definition);

InputStream readStream = s3.getObjectAsStream(definition);

s3.putObject(definition, "content to write");

s3.deleteObject(definition);
```

If you want to create an instances that logs in JSON format
you will need to create an instance of JsonLogSimpleS3 and pass a
Logger instance from [json logger](https://github.com/michaelruocco/json-logger):

```
Logger logger = LoggerFactory.getLogger("name");
SimpleS3 s3 = new JsonLogSimpleS3(AmazonS3ClientBuilder.standard().build(), logger);
```

There is also a class that will convert an S3Event into an ObjectDefinition
so that it can be easily used with this library:

```
S3Event s3event = ...
S3EventConverter converter = new S3EventConverter();
ObjectDefinition definition = converter.toObjectDefinition(s3Event);
```

Finally there is a FakeSimpleS3 implementation that can be used for testing
code that makes use of the SimpleS3 interface. There is also the BucketPoller class
that can be used when needing to test asynchronous operations that write a file
to a bucket, you can provide a RetryConfig class which specifies a maximum number
of retries and a wait delay, the poller will check the specified bucket, for an item
with the specified key, if it exists before the maximum number of retries is exceeded
it will return the file content as a string, if an unexpected error occurs it will throw
an AmazonS3Exception, otherwise if the object is not found it will throw an
ObjectNotFoundException.

## Running the Tests

You can run the tests for this project by running the following command:

```
gradlew clean build
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```
gradlew dependencyUpdates
```