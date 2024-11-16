library(httr)
library(jsonlite)

access <- readLines("./access.txt")

clientID <- access[1]
secret <- access[2]

################## GET ACCESS TOKEN ###################
get_access_token <- function() {
  headers <- c(
    'Content-Type' = 'application/x-www-form-urlencoded',
    'Accept' = 'application/json;charset=UTF-8'
  )
  
  body <- list(
    'grant_type' = 'client_credentials',
    'client_id'= clientID,
    'client_secret' = secret,
    'scope' = 'api_offresdemploiv2 o2dsoffre'
  )
  
  response <- VERB(
    "POST", 
    url = "https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=%2Fpartenaire",
    body = body,
    add_headers(headers),
    encode = 'form'
  )
  
  response_content <- content(response, as = "text")
  
  json_content <- fromJSON(response_content)
  
  return(json_content$access_token)
}

