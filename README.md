# NotificationService
This project's intent is to send emails to recipients with attachments. This is implemented in Spring Boot APIs.
To be used as a notification service by Amazon Price Track and Trigger Alert Project.

Source files are present in packages in location -> 

NotificationService/notificationspringrest/src/main/java/com/

## All the APIs in this project
1. @PostMapping("/Notifications/Mails/clientId/{clientId}")
   Post request to send emails to the recipients.
2. @GetMapping("/Notifications/clientId/{clientId}")
   Get all the emails sent by a specific client.
3. @GetMapping("/Notifications/Mails/clientId/{clientId}/startTime/{from}/endTime/{to}")
   Get all the emails sent by a specific client between a certain timeFrame.
4. @GetMapping("/Notifications/Mails/clientId/{clientId}/status/{status}")
   Get all the emails sent by a specific client filtered by status of the email.

## Future Work:
1. The attachment links are web links instead of local-links
2. Store the wishlist of a client
3. To be able to send an email with a table of wishlisted item name, price, link to the item


## Tutorial
1. Clone the repository and open the project with Eclipse
2. Make sure to add the password of the "from Email Address" in  the session object in the file: [EmailServiceImpl.java](https://github.com/sagaratla/NotificationService/blob/main/notificationspringrest/src/main/java/com/notificationspringrest/notificationspringrest/notificationservices/EmailServiceImpl.java)
3. Use Postman to use the API.
4. A sample JSON body for a post mapping (API - 1), Look at the "status" of the notification received - indicates if success or failure

```
[
    {
        "type": "MAIL",
        "subject": "Notification Service from Github",
        "mailBody": "Hi, How are you? This email has been sent from the notification service - https://github.com/sagaratla/NotificationService",
        "fromEmailId": "sampleFromEmailId@gmail.com",
        "toEmailId": "sampleToEmailId@gmail.com",
        "attachments": [
            {
                "attachmentLink": "D:\\Wallpapers\\1920x1080\\antimage___dota_2-wallpaper-1920x1080.jpg"
            },
            {
                "attachmentLink": "D:\\Wallpapers\\1920x1080\\assassins_creed_3_connor_bow-wallpaper-1920x1080.jpg"
            }
        ]
    }
]
```
