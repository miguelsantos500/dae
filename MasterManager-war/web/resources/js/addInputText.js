/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







// self executing function here
(function () {
    //

    var scientificTxtBoxes = 0;

    function removeScientificTxtBox(event) {
        event.preventDefault();
        event.stopPropagation();

        var parent = document.getElementById("scientificAreas");
        var textBox = document.getElementById("scientificTxtBox" + event.target.value);
        var button = document.getElementById(event.target.id);
        var br = document.getElementById("br" + event.target.value);
        parent.removeChild(textBox);
        parent.removeChild(button);
        parent.removeChild(br);


    }



    document.getElementById("j_idt10:addScientificArea").addEventListener("click", function (event) {
        event.preventDefault();
        event.stopPropagation();


        var foo = document.getElementById("scientificAreas");

        var br = document.createElement("br");
        br.setAttribute("id", "br" + scientificTxtBoxes);

        foo.appendChild(br);
        
        //Create an input type dynamically.
        var element = document.createElement("input");

        //Assign different attributes to the element.
        element.setAttribute("type", "Textbox");
        element.setAttribute("id", "scientificTxtBox" + scientificTxtBoxes);

        //Append the element in page (in span).
        foo.appendChild(element);

        var button = document.createElement("button");

        button.setAttribute("class", "btn btn-danger");
        button.setAttribute("onclick", "return false;");
        button.setAttribute("value", scientificTxtBoxes);
        button.setAttribute("id", "btnRemoveScientificTextBox" + scientificTxtBoxes);
        button.innerHTML = 'Remove';

        button.addEventListener("click", removeScientificTxtBox);

        foo.appendChild(button);


        scientificTxtBoxes++;

    });
    document.getElementById("j_idt10:submit").addEventListener("click", function (event) {

        var scientificAreasString = "";
        var scientificArea;

        for (i = 0; i < scientificTxtBoxes; i++) {
            
            var textBox = document.getElementById("scientificTxtBox" + i);
            
            if(textBox !== null) {
                scientificArea = textBox.value;
                if(scientificArea !== null && scientificArea !== "") {
                    scientificAreasString += scientificArea + ";";
                }
            }
            
        }

        var inputScientificAreas = document.getElementById("j_idt10:inputScientificAreas");
        
        inputScientificAreas.setAttribute("value", scientificAreasString);

    });






})();





