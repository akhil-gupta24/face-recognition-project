package com.s3objects.listing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListS3Objects {

	public static List<String> getS3ObjectsList() throws IOException {
		Regions clientRegion = Regions.DEFAULT_REGION;
		String bucketName = "face-database-collection-bucket";
		String prefix = "t";
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
				.withRegion(clientRegion).build();
		ObjectListing list = s3Client.listObjects(bucketName, prefix);
		// List of all images key name or image name
		List<String> imageNameList = new ArrayList<String>();
		do {

			List<S3ObjectSummary> summaries = list.getObjectSummaries();

			for (S3ObjectSummary summary : summaries) {

				String summaryKey = summary.getKey();
				imageNameList.add(summaryKey);
				System.out.println(imageNameList);
				// System.out.println(summaryKey);
			}
			// listNextBatchOfObjects Provides an easy way to continue a truncated object
			// listing and retrievethe
			// next page of results
			list = s3Client.listNextBatchOfObjects(list);

		} while (list.isTruncated());
		// isTruncated Gets whether or not this object listing is complete.
		// Returns The value true if the object listing is not complete. Returns the
		// value false if otherwise. When returning true, additional calls to Amazon S3
		// may be needed in order to obtain more results.
		return imageNameList;
	}
}

//----------------------------------------------------------------------------------------------------------//
//Regions clientRegion = Regions.DEFAULT_REGION;
//String bucketName = "akhil-demo-listing-objects";

//AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
//		.withRegion(clientRegion).build();
//ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
//		.withPrefix("i");
//ObjectListing objectListing;
//
//do {
//	objectListing = s3Client.listObjects(listObjectsRequest);
//	for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
//		System.out.println(
//				" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
//	}
//	listObjectsRequest.setMarker(objectListing.getNextMarker());
//} while (objectListing.isTruncated());
//}

//----------------------------------------------------------------------------------------------------------------------//

//List of all s3 bucket object
//Regions clientRegion = Regions.DEFAULT_REGION;
//String bucketName = "akhil-demo-listing-objects";
//
//// AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
//AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
//		.withRegion(clientRegion).build();
//ObjectListing listing = s3Client.listObjects(bucketName, "i");
//List<S3ObjectSummary> summaries = listing.getObjectSummaries();
//
//while (listing.isTruncated()) {
//	listing = s3Client.listNextBatchOfObjects(listing);
//	summaries.addAll(listing.getObjectSummaries());
//}
//System.out.println(summaries);
