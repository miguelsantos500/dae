/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function add() {

	//Create an input type dynamically.
	var element = document.createElement("input");

	//Assign different attributes to the element.
	element.setAttribute("type", "Textbox");
	element.setAttribute("value", "Textbox");
	element.setAttribute("name", "Textbox");


	var foo = document.getElementById("scientificAreas");

	//Append the element in page (in span).
	foo.appendChild(element);

}
