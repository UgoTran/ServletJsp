var loadFile = function(event) {
    var output = document.getElementById('photoOutput');
    output.src = URL.createObjectURL(event.target.files[0]);
};