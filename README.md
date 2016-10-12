# Java EE Auction web application
### Main functionality:
- Authenticated login
- Creating auctions
- Filtering auctions based on search text and category
- Placing bids on auctions
- Giving feedback to auctions you won
- View seller profile
- Overview of your bids
- Overview of your auctions

### Users
There are two types of users, sellers and customers.
Sellers can create auctions, and customers can bid on them. If a customer wins an auction, he or she can give feedback on the auction.
The sellers rating is calculated based on all feedback it has been given.
In addition, unauthenticated users can view ongoing auctions.

### Authentication
The users are authenticated through a login form. The passwords are SHA256 encrypted. 
We have included two generic user logins:
- Seller: username: 'seller', password: 'seller'
- Customer: username: 'customer', password: 'customer'

### Installation and setup
Building is done using Maven.
The project requires that the user installs Glasshfish Server v. 4.1.1.
When the project is run, it can be accessed at: 'localhost:8080:mod250_auction'

### Authors
The project is created by Didrik Kvanvik, Andreas Ravndal, Ole Eskild Steensen and André Mossige.
