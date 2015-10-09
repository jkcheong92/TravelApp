// Try HTML5 geolocation.

var image = 'images/house170.png';
var markers2 =[];


if (navigator.geolocation) {

    navigator.geolocation.getCurrentPosition(function (position) {

        var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
        };

        window.map = new google.maps.Map(document.getElementById('map'), {
            center: pos,
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
        });
        var marker = new google.maps.Marker({
            map: map,
            position: pos,
            animation: google.maps.Animation.DROP,
            icon:image
        });


        markers2.push(marker);
        setMapOnAll(map);
        setupSearch();
    })
}

function clearMarkers() {
    setMapOnAll(null);
}
function setMapOnAll(map) {
    for (var i = 0; i < markers2.length; i++) {
        markers2[i].setMap(map);
    }
}
function deleteMarkers() {
    clearMarkers();
    markers2 = [];
}



function locateMe() {

    navigator.geolocation.getCurrentPosition(function (position) {

        var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
        };

        var marker = new google.maps.Marker({
            map: map,
            position: pos,
            animation: google.maps.Animation.DROP,
            icon:image
        });


        deleteMarkers();
        markers2.push(marker);
        setMapOnAll(map);
        map.setCenter(pos);
    })
}

function getWeather() {

    navigator.geolocation.getCurrentPosition(function (position) {

        var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
        };

        var theUrl = 'http://api.openweathermap.org/data/2.5/weather';

        var paramString = "lat="+pos.lat+"&"+"lon="+pos.lng+"&units=metric";

        var APIKey = "8ed89d12856dd0e8636a7fe9f351a090";

        var xmlHttp = null;
        xmlHttp = new XMLHttpRequest();


        xmlHttp.onreadystatechange = function () {//Call a function when the state changes.
            // note: if async == false, do not write this function
            if (xmlHttp.readyState == 4) {
                //if (xmlHttp.status == 200 || xmlHttp.status == 0) {
                if (xmlHttp.status == 200) {
                    //var JSONDataPost2 = JSON.parse(xmlHttp.responseText);
                    AndroidWeather.showWeather(xmlHttp.responseText);


                }
            }
        };

        var getString = theUrl + "?" + paramString + "&APPKEY="+APIKey;

        xmlHttp.open("GET", getString, true);   // true means asynchronous

        xmlHttp.send(null);


    })
}

setupSearch = function() {
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    var infowindow = new google.maps.InfoWindow();
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

// Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });

    var markers = [];

    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function (marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function (place) {
            var icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            var marker = new google.maps.Marker({
                map: map,
                icon: icon,
                title: place.name,
                position: place.geometry.location
            });


            google.maps.event.addListener(marker, 'click', function() {
                var content = place.name+"<br>"+place.formatted_address+"<br>";
                infowindow.setContent(content);
                infowindow.addC
                infowindow.open(map, this);
            });

            markers.push(marker);

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });
};