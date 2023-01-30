function loadClassNames() {
    const selection = document.getElementById('classNameSelection');

    fetch('./classname/all')
        .then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log(data);

                data.forEach(className => {
                    const option = document.createElement('option');
                    option.text = className.classname;
                    option.value = className.classId;
                    selection.appendChild(option);
                });
            }) : console.error('Error while loading class names: ' + response.status + ' ' + response.statusText);
        })
        .catch(error => console.error(error));
}

function loadStudentsFromClass (classId) {
    const selection = document.getElementById('studentSelection');
    selection.innerHTML = '';

    console.log('Loading students for class ' + classId);

    fetch('./student/' + classId)
        .then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log(data);

                data.forEach(student => {
                    const option = document.createElement('option');
                    option.text = student.firstname + ' ' + student.lastname;
                    option.value = student.studentId;
                    selection.appendChild(option);
                });
            }) : console.error('Error while loading students: ' + response.status + ' ' + response.statusText);
        })
        .catch(error => console.error(error));
}

function loadExamsForStudent(studentId) {
    const tbody = document.getElementById('tbExam');
    tbody.innerHTML = '';

    console.log('Loading exams for student ' + studentId);

    fetch('./exam/' + studentId)
        .then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log(data);

                data.forEach(exam => {
                    console.log(exam);
                    const tr = document.createElement('tr');
                    tr.innerHTML = '<td>' + exam.subject.longname + '</td>';
                    tr.innerHTML += '<td>' + exam.dateOfExam + '</td>';
                    tr.innerHTML += '<td>' + exam.duration + '</td>';
                    tr.innerHTML += '<td></td>';
                    tbody.appendChild(tr);
                });
            }) : console.error('Error while loading exams: ' + response.status + ' ' + response.statusText);
        })
        .catch(error => console.error(error));
}