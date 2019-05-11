1.) Load source files using the REST API provided below as cURL

curl -i -X POST \
   -H "Content-Type:multipart/form-data" \
   -F "files=" \
   'http://localhost:8000/api/txn/load'

2.) Generate the summary report using the below REST API provided below as cURL

curl -i -X GET \
 'http://localhost:8000/api/txn/summaryReport'
