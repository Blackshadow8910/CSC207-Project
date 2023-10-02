# CSC207-Project-Description
This is a repository for our CSC207 project. For this project, we would like to work on a project domain based on an 
inventory management system for an electronics company. 

In developing this application, we would love to add features that include product descriptions such as the costs,
seller information and any other detail/ features related to the product we have in our inventory (what it does, 
how much is available for us to buy). We also plan to introduce an ordering function where, based on our current level 
of inventory, we can recommend items which need to be restocked. Going on a more complex route on our ordering, we can 
implement a cost-price algorithm which recommends a product to buy more of based on the analyses the trends of sales of 
a product (the amount sold and at what price), comparing it to the current seller's price and whether there is 
great profit to be gained. Some more basic uses of our inventory management system would be to use it to measure and 
record the amount of transactions (helps with audit trailing), calculating profits and losses, and lastly, forecasting 
(predicting the future inventory needs). 

A potential API we plan to use is called Digi-Key API, found at: https://developer.digikey.com/. DigiKey Corporation is 
an American corporation which deals with the distribution of electronics. We plan on using its api to collect information 
regarding their products which we can use in our business discussions. There are many other APIs which we can use to 
collect product information. Another example of this would be the BestBuy API, found at: https://bestbuyapis.github.io/api-documentation/#response-format.

Using Postman, we can try out the API by searching for a product listed on the DigiKey website (for example, we can get 
the product details for a HDMI cord of product number: 380-BC-HH006F-ND);

# Postman Screenshot
![postman.png](postman.png)

# Java Code

Using Postman, we are able to generate some code that would help us access the API and use it to provide us with details 
of the products, unfortunately, this code appears to have some errors and does not return a similar result of that, when 
using Postman. The output of the Postman API call is however included in the repository under the file name api_response.json.
The output of the API call is also given below:

```
{"MyPricing":[],"Obsolete":false,"MediaLinks":[{"MediaType":"Datasheets","Title":"BC-HH0xxF Drawing","SmallPhoto":null,"Thumbnail":null,"Url":"https://www.belfuse.com:443/resources/drawings/dr-bc-hhxxxf.pdf"},{"MediaType":"Product Photos","Title":"BC-HH0xxF","SmallPhoto":"https://mm.digikey.com/Volume0/opasdata/d220001/derivates/1/010/101/913/MFG_BC-HH0xxF_sml.jpg","Thumbnail":"https://mm.digikey.com/Volume0/opasdata/d220001/derivates/3/010/101/913/MFG_BC-HH0xxF_tmb.jpg","Url":"https://mm.digikey.com/Volume0/opasdata/d220001/medias/images/331/MFG_BC-HH0xxF.jpg"},{"MediaType":"Featured Product","Title":"HDMI Cable Assemblies","SmallPhoto":null,"Thumbnail":null,"Url":"https://www.digikey.com/product-highlight/b/bel/hdmi-cable-assemblies"},{"MediaType":"HTML Datasheet","Title":"BC-HH0xxF Drawing","SmallPhoto":null,"Thumbnail":null,"Url":"https://www.digikey.com/htmldatasheets/production/3824165/0/0/1/bc-hh010f.html"}],"StandardPackage":70,"LimitedTaxonomy":{"Children":[{"Children":[],"ProductCount":0,"NewProductCount":0,"ParameterId":-8,"ValueId":"462","Parameter":"Categories","Value":"Video Cables (DVI, HDMI)"}],"ProductCount":0,"NewProductCount":0,"ParameterId":-8,"ValueId":"21","Parameter":"Categories","Value":"Cable Assemblies"},"Kits":[],"KitContents":[],"MatingProducts":[],"SearchLocaleUsed":{"Site":"US","Language":"en","Currency":"CAD","ShipToCountry":""},"AssociatedProducts":[],"ForUseWithProducts":[],"RohsSubs":[],"SuggestedSubs":[],"AdditionalValueFee":0.0,"ReachEffectiveDate":"","ShippingInfo":"","StandardPricing":[{"BreakQuantity":1,"UnitPrice":9.66000,"TotalPrice":9.66},{"BreakQuantity":10,"UnitPrice":8.69700,"TotalPrice":86.97},{"BreakQuantity":25,"UnitPrice":8.19320,"TotalPrice":204.83},{"BreakQuantity":50,"UnitPrice":7.98300,"TotalPrice":399.15},{"BreakQuantity":100,"UnitPrice":7.77300,"TotalPrice":777.30},{"BreakQuantity":250,"UnitPrice":6.93264,"TotalPrice":1733.16},{"BreakQuantity":500,"UnitPrice":6.51248,"TotalPrice":3256.24},{"BreakQuantity":1000,"UnitPrice":5.75619,"TotalPrice":5756.19}],"RoHSStatus":"ROHS3 Compliant","LeadStatus":"Lead Status unavailable","Parameters":[{"ParameterId":7,"ValueId":"61","Parameter":"Packaging","Value":"Box"},{"ParameterId":37,"ValueId":"319929","Parameter":"Color","Value":"Black"},{"ParameterId":77,"ValueId":"250385","Parameter":"Length","Value":"6.0' (1.83m)"},{"ParameterId":80,"ValueId":"340303","Parameter":"Shielding","Value":"Double Shielded"},{"ParameterId":321,"ValueId":"397568","Parameter":"Cable Type","Value":"Round"},{"ParameterId":493,"ValueId":"354656","Parameter":"Usage","Value":"High Definition Multimedia Interface"},{"ParameterId":2350,"ValueId":"349292","Parameter":"1st Connector Mounting Type","Value":"Free Hanging (In-Line)"},{"ParameterId":2351,"ValueId":"349292","Parameter":"2nd Connector Mounting Type","Value":"Free Hanging (In-Line)"},{"ParameterId":2352,"ValueId":"719013","Parameter":"1st Connector Type","Value":"HDMI-A Male"},{"ParameterId":2353,"ValueId":"719013","Parameter":"2nd Connector Type","Value":"HDMI-A Male"}],"ProductUrl":"https://www.digikey.com/en/products/detail/bel-inc/BC-HH006F/10407217","PrimaryDatasheet":"https://www.belfuse.com:443/resources/drawings/dr-bc-hhxxxf.pdf","PrimaryPhoto":"https://mm.digikey.com/Volume0/opasdata/d220001/medias/images/331/MFG_BC-HH0xxF.jpg","PrimaryVideo":"","Series":{"ParameterId":-5,"ValueId":"818","Parameter":"Series","Value":"-"},"ManufacturerLeadWeeks":"11 week(s)","ManufacturerPageUrl":"https://www.digikey.com","ProductStatus":"Active","AlternatePackaging":[],"DetailedDescription":"Cable Assembly 6.0' (1.83m)","ReachStatus":"Reach unaffected","ExportControlClassNumber":"EAR99","HTSUSCode":"8544.42.2000","TariffDescription":"Tariff Applied","MoistureSensitivityLevel":"1  (Unlimited)","Family":{"ParameterId":-2,"ValueId":"462","Parameter":"Family","Value":"Video Cables (DVI, HDMI)"},"Category":{"ParameterId":-3,"ValueId":"21","Parameter":"Category","Value":"Cable Assemblies"},"ManufacturerPartNumber":"BC-HH006F","MinimumOrderQuantity":1,"NonStock":false,"Packaging":{"ParameterId":7,"ValueId":"61","Parameter":"Packaging","Value":"Box"},"QuantityAvailable":2534,"DigiKeyPartNumber":"380-BC-HH006F-ND","ProductDescription":"CABLE M-M HDMI-A 6' SHLD","UnitPrice":9.66,"Manufacturer":{"ParameterId":-1,"ValueId":"1847","Parameter":"Manufacturer","Value":"Bel Inc."},"ManufacturerPublicQuantity":0,"QuantityOnOrder":0,"MaxQuantityForDistribution":999999999,"BackOrderNotAllowed":false,"DKPlusRestriction":false,"Marketplace":false,"SupplierDirectShip":false,"PimProductName":"Article_13224211566934702","Supplier":"Bel Inc.","SupplierId":1847,"IsNcnr":false}
```

# Technical Problems

-As stated above the java code does not appear to give the same result as if we were to use the Postman tool to access 
DigiKey products and their details (output of the postman query is shown in the "api response.json" file). 

-Other potential problems that arose in this brainstorming session is that some API are paid and others (like the BestBuy API) 
require strict authorization to use. DigiKey was used as it was free (although it is quite complicated to access and setup) with no organization 
authorization needed. 

-If we were to use the other APIs instead, we would need more time to address these issues.


