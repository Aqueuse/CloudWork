library(httr)
library(jsonlite)

################## GET OFFRES DATA ###################
get_offres_data <- function(access_token) {
  url <- "https://api.francetravail.io/partenaire/offresdemploi/v2/offres/search"
  
  response <- VERB(
    "GET", 
    url,
    add_headers('Authorization' = 'Bearer '+access_token), 
    content_type("application/octet-stream"), 
    accept("application/json")
  )
  
  return(content(response, "text"))
}