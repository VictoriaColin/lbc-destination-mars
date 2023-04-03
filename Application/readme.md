aws cloudformation create-stack 
--stack-name dynamodbtabledesign-recipestables01 
--template-body file://DynamoDBTableDesign/IceCreamParlor/RecipesTable.yaml 
--capabilities CAPABILITY_IAM



aws cloudformation create-stack --stack-name Flight --template-body file://Application/Flight.yml --capabilities CAPABILITY_IAM


Application/PurchasedTicket.yml
Application/Flight.yml
Application/ReservedTicket.yml