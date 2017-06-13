/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    "use strict";
    //get values from json response

//    var jsondata = {
//        "images": [{
//                "COLTHUMBNAILNAME": "94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg",
//                "thumbnailpath": "http://localhost:8080/ScaleImage/thumbnails/94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg",
//                "COLIMAGENAME": "94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg",
//                "imagepath": "http://localhost:8080/ScaleImage/images/94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg"
//            }, {
//                "COLTHUMBNAILNAME": "048a426ad37a30f467d2ac1e06544e15bb45b63a.jpg",
//                "thumbnailpath": "http://localhost:8080/ScaleImage/thumbnails/048a426ad37a30f467d2ac1e06544e15bb45b63a.jpg",
//                "COLIMAGENAME": "048a426ad37a30f467d2ac1e06544e15bb45b63a.jpg",
//                "imagepath": "http://localhost:8080/ScaleImage/images/048a426ad37a30f467d2ac1e06544e15bb45b63a.jpg"
//            }]
//    };
//    console.log(jsondata.images[0].COLTHUMBNAILNAME);
//    $.each(jsondata, function (i, item) {
//
//        $.each(item, function (key, value) {
//            console.log(item[key].COLTHUMBNAILNAME);
//            $.each(value, function (i, item) {
//                console.log("Key is " + i + " Value is " + item);
//
//            });
//        });
//
//    });
    var postid = "ABCD";
    var setpostid = $("#id-colpostid").val(postid);
    var colpostid = $("#id-colpostid").val();
    //  console.log("PostId is " + colpostid);
    Dropzone.autoDiscover = false;
    $("form#id-image-upload").dropzone({
        url: "ImageUploader",
        addRemoveLinks: true,
        params: {
            COLPOSTID: colpostid
        },
        maxFilesize: 15.0,
        maxFiles: 30,
        autoProcessQueue: true,
        acceptedFiles: "image/*",
        dictInvalidFileType: "Image Type is Invalid !!",
        dictFileTooBig: "Image Size is too Big !!",
        dictMaxFilesExceeded: "Max Images Exceeded !!",
        init: function () {
            // Get this list of Files from the Server and Display it
            //   console.log("inside init event");
            Dropzone = this;
            var imagecount = Dropzone.options.maxFiles;
            //   console.log("Maxfiles image count original is " + imagecount);
            var ImageCount = $.getJSON("GetImagesCount?postid=" + colpostid, function (response) {
                //    console.log("Image Count at from data base is " + response.count + " for " + colpostid);
            });
            ImageCount.done(function (data) {
                //    console.log(data.count);
                var dbImageCount = data.count;
                if (dbImageCount >= imagecount) {
                    //Disable further uploads since maxfiles limit is reached
                    Dropzone.disable();
                    $("#id-image-count").text("You have uploaded " + data.count + " Image.You cannot upload Images further.");
                    $("#id-image-count").addClass("block");
                    $(".dz-message").text("Image upload limit has been reached !!");
                    $(".dz-message").addClass("block");
                    // Dropzone.options.dictDefaultMessage = "Image upload limit has been reached!!";
                }
                else {
                    $("#id-image-count").text("You have uploaded " + data.count + " Image ");
                }

            });
            ImageCount.fail(function (data) {
                //    console.log("Request failed " + data.status);
            });


            // Show the images from the database if they are present
            var ImageCount = $.getJSON("GetAllImages?postid=" + colpostid, function (response) {
                //     console.log("Images returned from database is " + response + " for " + colpostid);
                //      console.log("Nae is " + response.images[0].COLTHUMBNAILNAME);
                $.each(response, function (i, item) {
                    $.each(item, function (key, value) {
                        var mockFile = {name: item[key].COLTHUMBNAILNAME, size: 12345};
                        console.log("Mockfile name is " + item[key].COLTHUMBNAILNAME);
                        Dropzone.options.addedfile.call(Dropzone, mockFile);
                        Dropzone.options.thumbnail.call(Dropzone, mockFile, "thumbnails/" + mockFile.name);
                        Dropzone.emit("complete", mockFile);
                    });

                });

            });



            //Call the action method to load the images from the server
//            var mockFile = {name: "94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg", size: 12345};
//            Dropzone.options.addedfile.call(Dropzone, mockFile);
//            Dropzone.options.thumbnail.call(Dropzone, mockFile, "http://localhost:8080/ScaleImage/thumbnails/94e531dc50ac778223c8b976c5a8d25b31fc8664.jpg");
//            Dropzone.emit("complete", mockFile);

        },
        success: function (file, responseText) {
//            console.log("inside success events");
//            console.log("Response text is " + responseText.name);
            $(file.previewElement).find('[data-dz-name]').html(responseText.name);
            $("#id-image-count").empty;
            $("#id-image-count").text("You have uploaded " + responseText.count + " Image ");

        },
        removedfile: function (file) {
            //     console.log("inside removedfile event" + file.name);
            var imageId = $(file.previewElement).find('[data-dz-name]').text();
            console.log("deleted file name id is " + imageId);
            var COLPOSTID = $("#id-colpostid").val();
            $.ajax({
                type: 'GET',
                url: 'DeleteImage',
                data: {
                    delfile: imageId,
                    COLPOSTID: COLPOSTID
                },
                dataType: 'json',
                success: function (result) {
//                    console.log("Delete Image " + result.count);
//                    console.log('Inside delete ajax call');
                    $("#id-image-count").empty;
                    $("#id-image-count").text("You have uploaded " + result.count + " Image ");
                    var _ref;
                    return (_ref = file.previewElement) !== null ? _ref.parentNode.removeChild(file.previewElement) : void 0;
                }
            });
        },
        maxfilesreached: function (file) {
            //        console.log("inside maxfilesreached event " + file.name);

        },
        maxfilesexceeded: function (file) {
            //        console.log("inside maxfilesexceeded event " + file.name);
            //     this.removeFile(file);
        }
//        addedfile: function (file) {
//            console.log("inside addedfile event" + file.name);
//        },
//        error: function (file) {
//            console.log("inside error event" + file.name);
//        },
//        complete:function(file){
//             console.log("inside complete event" + file.name);
//        },
//        cancelled:function(file){
//            console.log("inside cancelled event" + file.name);
//        },

//        queuecomplete:function(file){
//            console.log("inside queuecomplete event");
//        },
//        sending:function(file, xhr, formData){
//            console.log("inside sending event" + formData + xhr + file.name);
//        }

    });
});
