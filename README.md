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
The users are authenticated through a login form based on j_security. The passwords are SHA256 encrypted. 
We have included two generic user logins:
- Seller: username: 'seller', password: 'seller'
- Customer: username: 'customer', password: 'customer'

### Installation and setup
Clone project from GitHub. Build using Maven ex. in Netbeans.
The project requires:
- Installation of Glasshfish Server v. 4.1.1.
- Setup postgresql databse in Netbeans.
  - Server name: 'data2.hib.no'
  - Port: 5433
  - Database name: 'db2016_grp14'
  - User: 'grp14'
  - Password: 'grp143008'
- Setup of jdbc authentication realm in Glasshfish. If you're familiar with Glassfish, this should be straight forward. More information can be found here: https://dzone.com/articles/jdbc-realm-and-form-based

When the project is run, it can be accessed at: 'localhost:8080/mod250_auction'

### Authors
The project is created by Didrik Kvanvik, Andreas Ravndal, Ole Eskild Steensen and André Mossige.
