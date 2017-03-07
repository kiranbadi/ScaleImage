/*
 * jQuery File Upload Plugin JS Example 6.7
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload();
    var activeUploads = $('#fileupload').fileupload('active');
    console.log(activeUploads);
    $('#fileupload').fileupload()
            .bind('fileuploadstart', function () {
                // disable submit
            })
            .bind('fileuploadprogressall', function (e, data) {
                if (data.loaded === data.total) {
                    // all files have finished uploading, re-enable submit
                }
            })
            .bind('fileuploadsubmit', function (e, data) {               

            });

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
            'option',
            'redirect',
            window.location.href.replace(
                    /\/[^\/]*$/,
                    '/cors/result.html?%s'
                    )
            );

    if (window.location.hostname === 'localhost:8089') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: '//localhost:8089/jQuery-File-Upload-Java/imgs/',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            process: [
                {
                    action: 'load',
                    fileTypes: /^image\/(gif|jpeg|png)$/,
                    maxFileSize: 20000000 // 20MB
                },
                {
                    action: 'resize',
                    maxWidth: 1440,
                    maxHeight: 900
                },
                {
                    action: 'save'
                }
            ]
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//localhost:8089/jQuery-File-Upload-Java/imgs/',
                type: 'HEAD'
            }).fail(function () {
                $('<span class="alert alert-error"/>')
                        .text('Upload server currently unavailable - ' +
                                new Date())
                        .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        $('#fileupload').each(function () {
            var that = this;
            $.getJSON(this.action, function (result) {
                if (result && result.length) {
                    $(that).fileupload('option', 'done')
                            .call(that, null, {result: result});
                }
            });
        });
    }

});