document.addEventListener("DOMContentLoaded", function(){
    // make it as accordion for smaller screens
    if (window.innerWidth < 992) {

        // close all inner dropdowns when parent is closed
        document.querySelectorAll('.navbar .dropdown').forEach(function(everydropdown){
            everydropdown.addEventListener('hidden.bs.dropdown', function () {
                // after dropdown is hidden, then find all submenus
                this.querySelectorAll('.submenu').forEach(function(everysubmenu){
                    // hide every submenu as well
                    everysubmenu.style.display = 'none';
                });
            })
        });

        document.querySelectorAll('.dropdown-menu a').forEach(function(element){
            element.addEventListener('click', function (e) {
                let nextEl = this.nextElementSibling;
                if(nextEl && nextEl.classList.contains('submenu')) {
                    // prevent opening link if link needs to open dropdown
                    e.preventDefault();
                    if(nextEl.style.display == 'block'){
                        nextEl.style.display = 'none';
                    } else {
                        nextEl.style.display = 'block';
                    }

                }
            });
        })
    }
    // end if innerWidth
});
// DOMContentLoaded  end

/*
$(document).ready(function() {
    $('.dropdown-item').hover(function() {
        $.getJSON("http://localhost:8080/subcategoriasMenu", {
            //nombreCategoria: $(this).text()
            nombreCategoria: 5
        }, function (data){
            console.log(data);
            if (data != null) {
                //let html = '<option value="#">Seleccione una subcategoria (Opcional)</option>'
                let len = data.length
                for (let i = 0; i < len; i++) {
                    html += '<li><a class="dropdown-item" href="#">'+ data[i].nombre +'</a></li>'
                    //html2 += '<option value="'+ data[i].idSubCategoria +'">'+ data[i].nombre +'</option>'
                }
                $('#subcategoria').html(html)
            } else {
                $('#subcategoria').html('<li><a class="dropdown-item" href="#">...</a></li>')
            }
        })
    });
});
 */