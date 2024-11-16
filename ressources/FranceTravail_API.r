#install.packages(c("httr", "jsonlite"), repos = "http://cran.us.r-project.org")

library(httr)
library(jsonlite)
library(RColorBrewer)

source("cloud.r")
source("access_token.r")

############# INIT #########################

generateCloud <- function() {
  response_code = 0
  
  token <- get_access_token()
  
  print(token)
  
#  data <- get_offres_data(token)
  
  # for (i in 1:5) {
  #   
  #   # response_code <- get_data
  #   
  #   if (response_code == 200 || response_code == 204) {
  #     break
  #   }
  #   
  #   else {
  #     Sys.sleep(2)
  #     
  #     # response_code <- get_data
  #   }
  # }
  
  # parse results
  # separate descriptions of the offers from the rest
  
  # create a list of descriptions text
  
  #descriptions <- list("","")
  
  #drawCloud(descriptions)
}

generateCloud()
