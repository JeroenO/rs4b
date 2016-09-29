/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let fileDisplayArea = document.getElementById('antwoord');
function readTextFile(file)
{
    let rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status === 0)
            {
                let allText = rawFile.responseText;
                fileDisplayArea.innerText = allText;
            }
        }
    };
    rawFile.send(null);
}

readTextFile("file:////home/jeroen/OscarWildeSalome");

