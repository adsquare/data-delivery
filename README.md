![logo](http://www.adsquare.com/logo_pos_rgb.png)

adsquare library for data providers, app-publishers and SSPs to support data transmission

# Welcome to the adsquare data-delivery library

This open-source library supports you to transmit data to the adsquare Audience Management Platform (AMP). The following use cases are currently supported:


**[Use Case 1 – Providing Bid-stream Data](https://github.com/adsquare/data-delivery/wiki/Use-Case-1-%E2%80%93-Providing-bid-stream-data)**

Providing bid-stream data is primarily used by Supply Side Platforms (SSPs) to transmit bid requests in a very efficient way to adsquare. This data is being used to to build audience segments and to enable adsquare to create a reach forecast for their targeting products for the actual SSP supply.
[Read more in the wiki here.](https://github.com/adsquare/data-delivery/wiki/Use-Case-1-%E2%80%93-Providing-bid-stream-data)

**[Use Case 2 – Providing Location Data](https://github.com/adsquare/data-delivery/wiki/Use-Case-2-%E2%80%93-Providing-Location-Data)**

Providing raw location data to adsquare enables advertisers to target POIs, build geo-behavioral segments and gain location insights as well as onboard offline data. 
[Read more in the wiki here.](https://github.com/adsquare/data-delivery/wiki/Use-Case-2-%E2%80%93-Providing-Location-Data)

**Use Case 3 – Providing segmented data (not yet supported via this library!)**

Monetization of any pre-segmented data connected to Mobile Advertising IDs. This method will be added to the new library soon and will be the prefered way of transmitting data to adsquare. The old CSV based approach will continue to work.

## Structure

This library consists of two different projects, which are:

1. The actual library called "lib-data-delivery", which provides the avro definition as well as bindings and supporting utilities for the actual data preparation.
2. Examples for typical scenarios and use cases.

## Communication

- If you **need help**, contact the [adsquare support](mailto:support@adsquare.com).
- If you **found a bug**, open an issue.
- If you **have a feature request**, open an issue.
- If you **want to contribute**, submit a pull request.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
