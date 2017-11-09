![logo](http://www.adsquare.com/logo_pos_rgb.png)

adsquare library for data providers, app-publishers and SSPs to support data transmission

# Welcome to the adsquare data-delivery library

This open-source library supports you to exchange data with the adsquare Audience Management Platform (AMP). The following use cases are currently supported:


**[Use Case 1 – Providing bid-stream data](https://github.com/adsquare/data-delivery/wiki/Use-Case-1-%E2%80%93-Providing-bid-stream-data)**

Providing bid-stream data is primarily used by Supply Side Platforms (SSPs) to transmit bid requests in a very efficient way to adsquare. This data is being used to to build behavioral audience segments and to enable adsquare to to create a realistic reach forecast for their targeting products for the actual SSP supply.
[Read more in the wiki here.](https://github.com/adsquare/data-delivery/wiki/Use-Case-1-%E2%80%93-Providing-bid-stream-data)

**[Use Case 2 – Providing Location Data](https://github.com/adsquare/data-delivery/wiki/Use-Case-2-%E2%80%93-Providing-Location-Data)**

Providing raw location data to adsquare helps adsquare to build geo-behavioral segments, provide location insights and footfall measurement as well as onboard offline data. 
[Read more in the wiki here.](https://github.com/adsquare/data-delivery/wiki/Use-Case-2-%E2%80%93-Providing-Location-Data)

**Use Case 3 – Providing segmented data (not yet supported via this library!)**

Monetization of online data connected to Mobile Advertising IDs (targeting product "360°") and/or offline data connected to location (targeting product "LIVE!").

**Use Case 4 – Providing Hashed Email (not yet supported via this library!)**

Providing hashed emails to support adsquare's identity graph, supporting adsquare's onboarding capabilities using hashed email as an identifier.

## Structure

This library consists of two different projects, which are:

1. The actual library called "lib-data-delivery", which provides the avro definition as well as bindings and supporting utilities for the actual data preparation.
2. Examples for typical scenarios and use cases.

If you need any support, please contact adsquare at support@adsquare.com


## Communication

- If you **need help**, post a question to the [adsquare support](mailto:support@adsquare.com).
- If you **found a bug**, open an issue.
- If you **have a feature request**, open an issue.
- If you **want to contribute**, submit a pull request.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
