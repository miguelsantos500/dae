/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







// self executing function here
(function () {
    //

    var scientificTxtBoxes = 0;
    var objectiveTxtBoxes = 0;
    var bibliographyItemBoxes = 0;
    var successRequirementTxtBoxes = 0;
    var supportTxtBoxes = 0;


    function addTextBox(event, prefix) {
        event.preventDefault();
        event.stopPropagation();

        var txtBoxesNumber;

        switch (prefix) {
            case "scientificArea":
                txtBoxesNumber = scientificTxtBoxes;
                break;
            case "objective":
                txtBoxesNumber = objectiveTxtBoxes;
                break;
            case "bibliographyItem":
                txtBoxesNumber = bibliographyItemBoxes;
                break;
            case "successRequirement":
                txtBoxesNumber = successRequirementTxtBoxes;
                break;
            case "support":
                txtBoxesNumber = supportTxtBoxes;
                break;
        }

        var foo = document.getElementById(prefix + "s");

        var br = document.createElement("br");
        br.setAttribute("id", "br" + txtBoxesNumber);

        foo.appendChild(br);

        //Create an input type dynamically.
        var element = document.createElement("input");

        //Assign different attributes to the element.
        element.setAttribute("type", "Textbox");
        element.setAttribute("id", prefix + "TxtBox" + txtBoxesNumber);

        //Append the element in page (in span).
        foo.appendChild(element);

        var button = document.createElement("button");

        button.setAttribute("class", "btn btn-danger");
        button.setAttribute("onclick", "return false;");
        button.setAttribute("value", txtBoxesNumber);
        button.setAttribute("id", "btnRemove" + prefix + "TextBox" + txtBoxesNumber);
        button.innerHTML = 'Remove';


        switch (prefix) {
            case "scientificArea":
                button.addEventListener("click", removeScientificTxtBox);
                break;
            case "objective":
                button.addEventListener("click", removeObjectiveTxtBox);
                break;
            case "bibliographyItem":
                button.addEventListener("click", removeBibliographyItemTxtBox);
                break;
            case "successRequirement":
                button.addEventListener("click", removeSuccessRequirementTxtBox);
                break;
            case "support":
                button.addEventListener("click", removeSupportTxtBox);
                break;
        }

        foo.appendChild(button);

        switch (prefix) {
            case "scientificArea":
                scientificTxtBoxes++;
                break;
            case "objective":
                objectiveTxtBoxes++;
                break;
            case "bibliographyItem":
                bibliographyItemBoxes++;
                break;
            case "successRequirement":
                successRequirementTxtBoxes++;
                break;
            case "support":
                supportTxtBoxes++;
                break;
        }
    }


    function removeScientificTxtBox(event) {
        removeTxtBox(event, "scientificArea");
    }

    function removeObjectiveTxtBox(event) {
        removeTxtBox(event, "objective");
    }

    function removeBibliographyItemTxtBox(event) {
        removeTxtBox(event, "bibliographyItem");
    }

    function removeSuccessRequirementTxtBox(event) {
        removeTxtBox(event, "successRequirement");
    }

    function removeSupportTxtBox(event) {
        removeTxtBox(event, "support");
    }


    function removeTxtBox(event, prefix) {
        event.preventDefault();
        event.stopPropagation();

        var parent = document.getElementById(prefix + "s");
        var textBox = document.getElementById(prefix + "TxtBox" + event.target.value);
        var button = document.getElementById(event.target.id);
        var br = document.getElementById("br" + event.target.value);
        parent.removeChild(textBox);
        parent.removeChild(button);
        parent.removeChild(br);
    }

    function parseTextBoxes(prefix) {

        var finalString = "";
        var itemString;

        var txtBoxesNumber;

        switch (prefix) {
            case "scientificArea":
                txtBoxesNumber = scientificTxtBoxes;
                break;
            case "objective":
                txtBoxesNumber = objectiveTxtBoxes;
                break;
            case "bibliographyItem":
                txtBoxesNumber = bibliographyItemBoxes;
                break;
            case "successRequirement":
                txtBoxesNumber = successRequirementTxtBoxes;
                break;
            case "support":
                txtBoxesNumber = supportTxtBoxes;
                break;
        }
        for (i = 0; i < txtBoxesNumber; i++) {

            var textBox = document.getElementById(prefix + "TxtBox" + i);

            if (textBox !== null) {
                itemString = textBox.value;
                if (itemString !== null && itemString !== "") {
                    finalString += itemString + ";";
                }
            }

        }

        var capitalizedPrefix = prefix.charAt(0).toUpperCase() + prefix.slice(1);

        var input = document.getElementById("j_idt10:input" + capitalizedPrefix + "s");

        input.setAttribute("value", finalString);

    }


    document.getElementById("j_idt10:addScientificArea").addEventListener("click", function (event) {
        addTextBox(event, "scientificArea");
    });
    document.getElementById("j_idt10:addObjective").addEventListener("click", function (event) {
        addTextBox(event, "objective");
    });
    document.getElementById("j_idt10:addBibliographyItem").addEventListener("click", function (event) {
        addTextBox(event, "bibliographyItem");
    });
    document.getElementById("j_idt10:addSuccessRequirement").addEventListener("click", function (event) {
        addTextBox(event, "successRequirement");
    });
    document.getElementById("j_idt10:addSupport").addEventListener("click", function (event) {
        addTextBox(event, "support");
    });


    document.getElementById("j_idt10:submit").addEventListener("click", function (event) {
        parseTextBoxes("scientificArea");
        parseTextBoxes("objective");
        parseTextBoxes("bibliographyItem");
        parseTextBoxes("successRequirement");
        parseTextBoxes("support");
        return false;
    });




})();





