<%-- 
    Document   : index
    Created on : Feb 14, 2016, 11:50:05 PM
    Author     : Kiran
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dropzone Sample Example</title>

        <!-- Bootstrap -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" href="css/dropzone.css">

        <style type="text/css">
            .block{
                font-size:16px;
                color:red;
                margin-bottom:3px;
                font-weight:bold;
                letter-spacing:1px;
            }
            .jumbotron{
                padding: 30px;
                margin-bottom: 30px;
                font-size: 21px;
                font-weight: 200;
                line-height: 2.1428571435;
                color: inherit;
                background-color:transparent;
            }
        </style>



        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
              <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
              <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->
    </head>
    <body style="padding:10px;">
        <div class="container-fluid">

            <!-- Start of dropzone attached with form-->
            <div class="jumbotron" style="height:300px;overflow:auto;background-color:none;">
                <h4 class="text-center" id="id-image-count"></h4>
                <form action="ImageUploader" method="post" enctype="multipart/form-data" class="dropzone" id="id-image-upload" style="border-radius:10px;">
                    <input type="hidden" id="id-colpostid" value="" name="COLPOSTID">
                    <div class="fallback">
                        <input name="file" type="file" multiple />
                    </div>
                </form>
            </div>
            <!-- End of dropzone attached with form-->

            <!-- Start of dropzone attached with div-->
            <!--            <div class="jumbotron" style="height:300px;overflow:auto;">
                            <div id="id-image-upload" class="dropzone">
                                <input type="hidden" id="id-colpostid" value="ABCD" name="COLPOSTID">
                                <div class="fallback">
                                    <input name="file" type="file" multiple />
                                </div>
                            </div>
                        </div>-->

            <!--End of dropzone attached with div-->



        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!--<script src="../js/app/UpoadMedia.js"></script>-->
        <!-- Include all compiled plugins (below), or include individual files as needed --> 
        <script src="js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/4.3.0/dropzone.js"></script>
        <!--        <script src="js/dropzone.js"></script>-->
        <script src="js/app/dzImpl.js" type="text/javascript"></script>
        <script type="text/javascript">

        </script>
    </body>
</html>
