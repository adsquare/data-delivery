![logo_pos_rgb.png](https://bitbucket.org/repo/ng7kdoq/images/2807468506-logo_pos_rgb.png)

# Welcome to the adsquare data-delivery library

This open-source library supports you to exchange data with the adsquare Audience Management Platform (AMP). The following use cases are currently supported:

**Use Case 1 – Providing segmented data**

Monetization of online data connected to Mobile Advertising IDs (targeting product "360°") and/or offline data connected to location (targeting product "LIVE!").

**UC 2 – Providing Location Data**

Providing raw location data to support adsquare's location graph, supporting adsquare's onboarding capabilities using location as a proxy.

**Use Case 3 – Providing Hashed Email**

Providing hashed emails to support adsquare's identity graph, supporting adsquare's onboarding capabilities using hashed email as an identifier.

**[Use Case 4 – Providing bid-stream data](https://bitbucket.org/adsquare/data-delivery/wiki/Use%20Case%204%20%E2%80%93%20Providing%20bid-stream%20data)**

Providing bid-stream data is primarily used by Supply Side Platforms (SSPs) to transmit bid requests in a very efficient way to adsquare. This data is being used to support adsquare's onboarding capabilities as well as to support adsquare to create a realistic reach forecast for supply-side enrichment.
[Read more in the wiki here.](https://bitbucket.org/adsquare/data-delivery/wiki/Use%20Case%204%20%E2%80%93%20Providing%20bid-stream%20data)

## Structure

This library consists of two different projects, which are:

1. The actual library called "lib-data-delivery", which provides the avro definition as well as bindings and supporting utilities for the actual data preparation.
2. Examples for typical scenarios and use cases.

If you need any support, please contact adsquare at support@adsquare.com

Have fun!
