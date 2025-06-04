function fetchData(module) {
    
    let unitId = $('#principalEmployer').val();
    if (!module) return;
	// Get module name (selected option's text)
	  let moduleName = $('#module option:selected').text();
    $.ajax({
        url: '/CWFM/export/fetchModuleData',
        method: 'GET',
        data: {
            module: moduleName.trim(),
            unitId: unitId
        },
        success: function (response) {
            let columns = response.columns;
            let rows = response.rows;
            let headerHtml = '<th><input type="checkbox" id="selectAll" onchange="toggleExportSelectAll()"/></th>';
            columns.forEach(col => { headerHtml += `<th>${col}</th>`; });
            $('#tableHeader').html(headerHtml);

            let bodyHtml = '';
            rows.forEach(row => {
                bodyHtml += '<tr><td><input type="checkbox" class="rowCheckbox" name="selectedUnitIds"  /></td>';
                columns.forEach(col => { bodyHtml += `<td>${row[col]}</td>`; });
                bodyHtml += '</tr>';
            });
            $('#tableBody').html(bodyHtml);

            $('#dynamicTable').show();
            $('#exportBtn').show();
        }
    });
}

/*$(document).on('change', '#selectAll', function () {
    $('.rowCheckbox').prop('checked', this.checked);
});*/

function toggleExportSelectAll() {
           var selectAllCheckbox = document.getElementById('selectAll');
           var checkboxes = document.querySelectorAll('input[name="selectedUnitIds"]');
           checkboxes.forEach(function(checkbox) {
               checkbox.checked = selectAllCheckbox.checked;
           });
       }

function exportModuleCSV() {
    let csv = '';
    let headers = [];
    $('#dynamicTable thead th').each(function (index) {
        if (index === 0) return;
        headers.push($(this).text());
    });
    csv += headers.join(',') + '\n';

    $('#dynamicTable tbody tr').each(function () {
        if ($(this).find('.rowCheckbox').is(':checked')) {
            let row = [];
            $(this).find('td:not(:first-child)').each(function () {
                row.push($(this).text());
            });
            csv += row.join(',') + '\n';
        }
    });

    let blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    let link = document.createElement("a");
	let moduleName = $('#module option:selected').text();
    link.href = URL.createObjectURL(blob);
    link.download = moduleName.trim() + ".csv";
    link.click();
}