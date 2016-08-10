/**
 *
 * Created by maks on 05.08.16.
 *
 * Functions that provide Google Maps API
 *
 */

const DEFAULT_ZOOM_LEVEL = 16;

function initGoogleMaps() {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -33.8688, lng: 151.2195},
        zoom: DEFAULT_ZOOM_LEVEL,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    // Create the search box and link it to the UI element.
    var input = document.getElementById('location');
    var searchBox = new google.maps.places.SearchBox(input);
    var markers = [];
    var $checkboxShowMap = $('.Page').find('#show-map');
    var $mapHolder = $('.Page').find('#map-holder');
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            $checkboxShowMap.find('input').prop('checked', false);
            $checkboxShowMap.find('input').prop('disabled', true);
            $mapHolder.hide();
            return;
        }
        
        place = places[0];
        // Clear out the old markers.
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];

        $checkboxShowMap.find('input').prop('disabled', false);
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
        map.setZoom(DEFAULT_ZOOM_LEVEL);
    });

    return map;
}

function setLocationByAddress(map, address) {
    var geocoder = new google.maps.Geocoder();
    var $checkboxShowMap = $('.Page').find('#show-map');
    geocoder.geocode({'address': address}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            $checkboxShowMap.find('input').prop('disabled', false);
            map.setCenter(results[0].geometry.location);
            map.setZoom(DEFAULT_ZOOM_LEVEL);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
        } else {
            $checkboxShowMap.find('input').prop('disabled', true);
        }
    });
}