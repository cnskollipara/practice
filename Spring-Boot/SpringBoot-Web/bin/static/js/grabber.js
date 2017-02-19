/*function myInit(){
  var set_options ={
        stroke: true,
        scaleMap:true,
        strokeColor: 'ff0000',
        strokeOpacity: 1,
        singleSelect: true,
        fillColor: '222222',
        render_highlight: {
            fillOpacity: 0,
            strokeWidth: 1
        },
        render_select: {
            fillOpacity: 0,
            strokeWidth: 1.5
        }
    } 
  $('#uiScreen').mapster(set_options);
  //onWindowResize();
  $('#uiScreen').mapster('resize',410);
}*/
/*var resizeTime = 100;     // total duration of the resize effect, 0 is instant
var resizeDelay = 100;
function resize(maxWidth,maxHeight) {
     var image =  $('img'),
        imgWidth = image.width(),
        imgHeight = image.height(),
        newWidth=0,
        newHeight=0;
  
    if (imgWidth/maxWidth>imgHeight/maxHeight) {
        newWidth = maxWidth;
    } else {
        newHeight = maxHeight;
    }
    image.mapster('resize',newWidth,newHeight,resizeTime);   
}

// Track window resizing events, but only actually call the map resize when the
// window isn't being resized any more

function onWindowResize() {
    
    var curWidth = $(window).width(),
        curHeight = $(window).height(),
         checking=false;
     if (checking) {
         return;
             }
     checking = true;
     window.setTimeout(function() {
        var newWidth = $(window).width(),
           newHeight = $(window).height();
        if (newWidth === curWidth &&
            newHeight === curHeight) {
            resize(newWidth,newHeight); 
        }
         checking=false;
     },resizeDelay );
}

$(window).bind('resize',onWindowResize);*/

/*Angular Code Starts*/
var app = angular.module('Grabber', ['ngResource']);

app.directive('ngLoad', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('load', function() {
               scope.myInit();
            });
        }
    };
});

app.controller('Ctrl', function ($scope, $resource) {
  $scope.selected = [];

  $scope.myInit = function(){
    var set_options ={
          stroke: true,
          scaleMap:true,
          strokeColor: 'ff0000',
          strokeOpacity: 1,
          singleSelect: true,
          fillColor: '222222',
          render_highlight: {
              fillOpacity: 0,
              strokeWidth: 1
          },
          render_select: {
              fillOpacity: 0,
              strokeWidth: 1.5
          }
      }
      if($scope.orientation == "landscape") {
        $('img').addClass("landscape");
        $('img').mapster(set_options);
        //onWindowResize();
        $('img').mapster('resize',410);
      }else{
        $('img').removeClass("landscape");      
        $('img').mapster(set_options);
        //onWindowResize();
        $('img').mapster('resize',410);
      }
  }

  $scope.launch = function () {
      $("#overlay").show();
      var launchApp = $resource('http://localhost:8080/grabber/launch', {}, {
        get: {
          method: 'GET',
          transformResponse: function(data, headers){
            return data;
          },
        }
      });
      launchApp.get({},function success(data){       
        $("#launched").show();
        $('#launch').hide();
        $("#overlay").hide();
        console.log("Launched Application Successfully : " + data);
      }, function error(data){
        $scope.dt = data;
        $("#overlay").hide();
        console.log("Error : " + data);
      });
  }

  $scope.highlightUIElement = function(obj){
    var selector = "area[title='" + obj.bounds + "']";    
    $(selector).trigger("mouseover");
  }

  $scope.elementClicked = function(obj){
    /*var f = $scope.selected.filter(function(a) {return a.bounds === obj.bounds});
      if(f.length > 0) {
        return;
      }
      else {
        $scope.selected.push(obj);
      }*/
      /*Approach 2*/
      for (var i = 0; i < $scope.selected.length; i++) {
          if ($scope.selected[i] === obj) {
              return;
          }
      }

      $scope.selected.push(obj);
  }

  $scope.grab = function () {
      $("#overlay").show();
      delete $scope.uielements;
      var grabElements = $resource('http://localhost:8080/grabber/grab', {}, {
        get: {
          method: 'GET',
        }
      });
      grabElements.get({},function success(data){         
        $scope.uielements = data.uielements;
        $scope.uiScreen = data.screenshot;
        $scope.orientation = data.orientation;
        $("#overlay").hide();        
        console.log("Grabbed Successfully : " + data);
      }, function error(data){
        $scope.dt = data;
        $("#overlay").hide();
        console.log("Error : " + data);
      });
  }

  $scope.kill = function () {
      $("#overlay").show();
      var killApp = $resource('http://localhost:8080/grabber/kill', {}, {
        get: {
          method: 'GET',
          transformResponse: function(data, headers){
            return data;
          },
        }
      });
      killApp.get({},function success(data){      
        $("#launched").hide();        
        // $('img').attr('src', '');
        $('#launch').show();
        $("#overlay").hide();
        console.log("Killed Application Successfully : " + data);
        }, function error(data){
        $scope.dt = data;
        $("#overlay").hide();
        console.log("Error : " + data);
      });           
  }
});

angular.element(document).ready(function() {
  $("#overlay").hide();
  angular.bootstrap(document, ['Grabber']);
});