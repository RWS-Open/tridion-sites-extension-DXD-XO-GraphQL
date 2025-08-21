UDP Content XO Module:
---------------------

udp-xo-query-extension is a Tridion Sites Unified Delivery Platform (UDP) module that adds a custom GraphQL query for promotions.
It introduces the xoPromotion GraphQL type, fetching promotion data from Opensearch instead of the default Tridion Content Broker.

Features:
--------

> New GraphQL query: xoPromotion.

> Returns:

	a) Total hits count.

	b) Promotion details (id, name, index, etc.).

> Connects securely to Opensearch with configurable credentials.

> Integrated with UDP Content Service


Configuration:
-------------

> The module reads connection details from "application.properties" (Content Service).

Installation:
------------

1) Build the module JAR: (udp-xo-query-extension).

	> clean compile package install (Generate jar file)
	
2) Create module package: (udp-xo-query-extension-assembly).

	> clean compile package (Generate zip file)
	
3) Upload the zip file into Tridion addon-service.

4) Restart Content Service.

5) Verify schema in GraphiQL.

Example GraphQL Query:
---------------------

		{
		  xoPromotion {
			__typename
			hits {
			  total {
				value
			  }
			  hits {
				source {
				  id
				  name
				}
			  }
			}
		  }
		}
		
Output Example:
--------------


  "data": {
    "xoPromotion": {
      "__typename": "XoPromotions",
      "hits": {
        "total": {
          "value": 7
        },
        "hits": [
          {
            "source": {
              "id": "<promotion_id>",
              "name": "<promotion_name>"
            }
          }
		]
	  }
	}
  }