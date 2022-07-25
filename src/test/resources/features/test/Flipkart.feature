

Feature: Print search result products list from Flipkart.com
  Scenario Outline: Print search result for Samsung Galaxy S10
    Given Using Selenium load the flipkart.com desktop home page
    When Search for the <Product> on that page
    And On the search results click on Mobiles in categories
    When Apply the following filters Samsung and select Flipkart assured
    And  Sort the entries with Price -> High to Low
    When Read the set of results that show up on page 1
    Then Create a list with the Product Name,Display Price,Link to Product Details Page and print this on the console
    Examples:
      |Product  |
      |Samsung Galaxy S10 |

