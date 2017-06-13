<%-- 
    Document   : awsform
    Created on : Jun 10, 2017, 11:19:11 PM
    Author     : Kiran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="functions" %>
<!DOCTYPE html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AWS Proof of Concept</title>
        <!-- Bootstrap -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
                  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
                  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
                <![endif]-->
        <style type="text/css">
            body{
                padding:20px;
            }
            .btn{
                margin-right: 20px;

            }
            .row{
                margin-top:10px;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading"><strong>AWS Form Proof of Concept</strong></div>
                <div class="panel-body">

                    <!-- Standar Form -->
                    <h2 class="text-center">AWS Forms - Check for all form elements</h2>
                    <hr>
                    <form action="AWSController" method="post" id="js-upload-form">
                        <input type="hidden" name="hiddenfield" id="id-hidden-field" value="hidden">
                        <div class="row">
                            <div class="form-group col-md-3">
                                <label for="text">Input Text Field</label>
                                <input type="text" name="txtname" class="form-control" id="id-input-text" placeholder="Input Text Field">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="email">Input Email Field</label>
                                <input type="email" name="txtemail" class="form-control" id="id-input-email" placeholder="Input Email Field">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="date">Input Date Field</label>
                                <input type="date" name="txtdate" class="form-control" id="id-input-date">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="datetime">Input Datetime Field</label>
                                <input type="datetime" name="txtdatetime"  class="form-control" id="id-input-datetime">
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-3">
                                <label for="color">Input Color Field</label>
                                <input type="color" name="txtcolor" class="form-control" id="id-input-color">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="month">Input Month Field</label>
                                <input type="month" name="txtmonth" class="form-control" id="id-input-month">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="number">Input Number Field</label>
                                <input type="number" name="txtnumber" class="form-control" id="id-input-number" placeholder="Input number Field">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="week">Input Week Field</label>
                                <input type="week" name="txtweek" class="form-control" id="id-input-week">
                            </div>

                        </div>

                        <div class="row">
                            <div class="form-group col-md-3">
                                <label for="url">Input URL Field</label>
                                <input type="url" name="txturl" class="form-control" id="id-input-url" placeholder="Input url Field">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="time">Input Time Field</label>
                                <input type="time" name="txttime" class="form-control" id="id-input-time">
                            </div>

                            <div class="form-group col-md-3">
                                <label for="tel">Input tel Field</label>
                                <input type="tel" name="txttel" class="form-control" id="id-input-tel" placeholder="Input tel Field">
                            </div>

                            <div class="form-group col-md-3">
                                <label for="search">Input Search Field</label>
                                <input type="search" name="txtsearch" class="form-control" id="id-input-seach" placeholder="Input search Field">
                            </div>

                        </div>

                        <div class="row">
                            <div class="form-group col-md-3">
                                <label for="range">Input Range Field</label>
                                <input type="range" name="txtrange" min="10" max="100" class="form-control" id="id-input-range">
                            </div>
                            <div class="form-group col-md-3">
                                <label for="password">Input Password Field</label>
                                <input type="password" name="txtpassword" class="form-control" id="id-input-password" placeholder="Input Password Field">
                            </div>

                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="text">TextArea:</label>
                                <textarea name="txttextarea" class="form-control" rows="5" id="id-input-textarea"                                      style="resize:vertical;"
                                          ></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label>Checkbox</label>
                                <label class="checkbox-inline"><input type="checkbox" name="checkbox" value="Option1">Option 1</label>
                                <label class="checkbox-inline"><input type="checkbox" name="checkbox" value="Option2">Option 2</label>
                                <label class="checkbox-inline"><input type="checkbox" name="checkbox" value="Option3">Option 3</label>
                            </div>


                            <div class="form-group col-md-6">
                                <label>Radio Button Group</label>
                                <label class="radio-inline"><input type="radio" name="radio" value="Radio1">Option 1</label>
                                <label class="radio-inline"><input type="radio" name="radio" value="Radio2">Option 2</label>
                                <label class="radio-inline"><input type="radio" name="radio" value="Radio3">Option 3</label>

                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-3">
                                <label for="selectsingle">Select list:</label>
                                <select class="form-control" id="selectsingle" name="txtsingleselect">
                                    <option value="select1">Select1</option>
                                    <option value="select2">Select2</option>
                                    <option value="select3">Select3</option>
                                    <option value="select4">Select4</option>
                                </select>
                            </div>

                            <div class="formgroup col-md-3">
                                <label for="multiselect"></label>        			
                                <select id="multiselect" name="txtmultiselect" multiple="multiple" style="margin-top:20px;">
                                    <option value="multiselect1">Multiselect Option 1</option>
                                    <option value="multiselect2">Multiselect Option 2</option>
                                    <option value="multiselect3">Multiselect Option 3</option>
                                    <option value="multiselect4">Multiselect Option 4</option>
                                    <option value="multiselect5">Multiselect Option 5</option>
                                    <option value="multiselect6">Multiselect Option 6</option>
                                </select>        			
                            </div>

                        </div>

                        <div class="row">
                            <div class="form-group text-center">
                                <input type="submit" name="inputsubmit" class="btn btn-primary" value="InputSubmit">

                                <input type="reset" name="inputreset" class="btn btn-default" value="InputReset">

                                <button class="btn btn-primary btn-large" type="submit">SubmitButton</button>
                            </div>
                        </div>
                    </form>






                </div>
            </div>
        </div> <!-- /container -->
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

        <!-- Include all compiled plugins (below), or include individual files as needed --> 
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
