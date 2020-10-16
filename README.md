# TTC Finch Station Routes

An Android App that fetches and displays results of Finch Station routes. Written in Kotlin.

## Features / Extra Work Done
- **Pull-to-Refresh on Landing screen**

  Using the Pull-to-Refresh gesture, users can refresh the landing screen to request for new data.

- **Hide stops without routes**

  Stops that do not have routes are initially hidden to make it easier for users to browse all the routes. This option can be toggled off to view all the stops.

- **Grouped stops with the same shape (path)**

  On click of a route, all stops with the same path are grouped together so that all the stop times can be seen easily. 
  
- **Container transform transition**

  The container transform transition was applied when opening the Details screen so that users can see a connection between the clicked route and the Details screen.

- **Show ETA of next stop**

  The estimated time of arrival of the next stop is computed and displayed to quickly see the remaining time before the next stop.

- **Details screen refreshes every 30 seconds**

  A background process will run upon opening the Details screen to update the ETA and remove past stop times every 30 seconds.

- **Error messages**

  An alert will be displayed in case an error was encountered when requesting for data from the TTC API.
