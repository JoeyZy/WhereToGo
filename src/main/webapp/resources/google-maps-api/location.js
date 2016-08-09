/**
 *
 * Created by maks on 05.08.16.
 *
 * Functions that provide Google Maps API
 *
 */

const CUSTOM_ZOOM_LEVEL = 16;

// We have CheckBox as an argument because we
// give it to the SearchBox listener.
// When SearchBox gives no results or is empty,
// we have nothing to show on the map and disable the CheckBox.

function initGoogleMaps($checkboxShowMap) {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -33.8688, lng: 151.2195},
        zoom: CUSTOM_ZOOM_LEVEL,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    // Create the search box and link it to the UI element.
    var input = document.getElementById('location');
    var searchBox = new google.maps.places.SearchBox(input);

    var markers = [];

    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            $showArchiveCheckbox.find("input").prop('disabled', true);
            return;
        }

        $showArchiveCheckbox.find("input").prop('disabled', false);
        place = places[0];
        // Clear out the old markers.
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];

        var bounds = new google.maps.LatLngBounds();
        var icon = {
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(25, 25)
        };

        markers.push(new google.maps.Marker({
            map: map,
            icon: icon,
            title: place.name,
            position: place.geometry.location
        }));

        if (place.geometry.viewport) {
            // Only geocodes have viewport.
            bounds.union(place.geometry.viewport);
        } else {
            bounds.extend(place.geometry.location);
        }
        map.fitBounds(bounds);
        map.setCenter(place.geometry.location);
        map.setZoom(CUSTOM_ZOOM_LEVEL);
    });

    return map;
}

function setLocationByAddress(map, address) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            map.setZoom(CUSTOM_ZOOM_LEVEL);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}