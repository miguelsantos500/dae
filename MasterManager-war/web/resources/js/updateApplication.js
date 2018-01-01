/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



(function () {
    lblFileCV = document.getElementById("j_idt15:lblFileCV");
    lblFilePL = document.getElementById("j_idt15:lblFilePL");
    lblFileCert = document.getElementById("j_idt15:lblFileCert");
    fuCV = document.getElementById("j_idt15:fuCV_input");
    fuPL = document.getElementById("j_idt15:fuPL_input");
    fuCert = document.getElementById("j_idt15:fuCert_input");

    checkBoxReplaceCV = document.getElementById("j_idt15:checkBoxReplaceCV");
    checkBoxReplacePL = document.getElementById("j_idt15:checkBoxReplacePL");
    checkBoxReplaceCert = document.getElementById("j_idt15:checkBoxReplaceCert");


    toggleElements(checkBoxReplaceCV, lblFileCV, fuCV);
    toggleElements(checkBoxReplacePL, lblFilePL, fuPL);
    toggleElements(checkBoxReplaceCert, lblFileCert, fuCert);



    function toggleElements(checkBoxReplace, lblFile, fu) {
        if (checkBoxReplace.checked) {
            lblFile.style.visibility = "visible";
            fu.parentNode.style.visibility = "visible";
        } else {
            lblFile.style.visibility = "hidden";
            fu.parentNode.style.visibility = "hidden";
        }
    }

    checkBoxReplaceCV.addEventListener("click", function (event) {
        toggleElements(checkBoxReplaceCV, lblFileCV, fuCV);
    });

    checkBoxReplacePL.addEventListener("click", function (event) {
        toggleElements(checkBoxReplacePL, lblFilePL, fuPL);
    });

    checkBoxReplaceCert.addEventListener("click", function (event) {
        toggleElements(checkBoxReplaceCert, lblFileCert, fuCert);
    });


})();