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

function loadStudents(classId, pageNo) {
    document.getElementById('examAction').disabled = true;

    fetch('./student?' + new URLSearchParams({
        classId: classId,
        pageNo: pageNo,
        pageSize: 10
    }))
        .then(response => response.json())
        .then(data => {
            console.log(data);

            const studentPagination = document.getElementById('studentPagination');
            studentPagination.innerHTML = '';

            for (let i = 0; i < data.totalPages; i++) {
                const li = document.createElement('li');
                li.classList.add('page-item');
                li.innerHTML = `<a class="page-link" onclick="loadStudents(${classId},${i})">${i + 1}</a>`;
                studentPagination.appendChild(li);
            }

            const radStudents = document.getElementById('radStudents');
            radStudents.innerHTML = '';

            data.content.forEach(student => {
                const lbl = document.createElement('label');
                const rad = document.createElement('input');
                rad.type = 'radio';
                rad.name = 'student';
                rad.required = true;
                rad.value = student.studentId;
                rad.onclick = function () {
                    loadExamsForStudent(student.studentId)
                };

                lbl.appendChild(rad);
                lbl.append(student.firstname + ' ' + student.lastname);
                radStudents.appendChild(lbl);
                radStudents.appendChild(document.createElement('br'));
            })
        })
        .catch(error => console.error(error));
}

function loadExamsForStudent(studentId) {

    const tbody = document.getElementById('tbExam');
    tbody.innerHTML = '';

    console.log('Loading exams for student ' + studentId);

    fetch('./exam/student/' + studentId)
        .then(response => {
            document.getElementById('examAction').disabled = false;
            if (response.status === 204) {
                console.log('No exams found for student ' + studentId);
                alert('No exams found for student ' + studentId);
                return;
            }
            response.status === 200 ? response.json().then(data => {
                console.log(data);

                data.forEach(exam => {
                    console.log(exam);
                    const tr = document.createElement('tr');
                    tr.innerHTML = '<td>' + exam.examId + '</td>';
                    tr.innerHTML += '<td>' + exam.subject.longname + '</td>';
                    tr.innerHTML += '<td>' + exam.dateOfExam + '</td>';
                    tr.innerHTML += '<td>' + exam.duration + '</td>';
                    tr.innerHTML += `<td><button onclick="updateExam(${exam.examId}, ${exam.dateOfExam}, ${exam.duration}, ${exam.subject.subjectId})" class="btn btn-info">Edit</button><button onclick="deleteExam(${exam.examId})" class="btn btn-danger">Delete</button></td>`;
                    tbody.appendChild(tr);
                });

            }) : console.error('Error while loading exams: ' + response.status + ' ' + response.statusText);
        })
        .catch(error => console.error(error));
}

function deleteExam (examId) {
    fetch('./exam/delete/' + examId, {
        method: 'DELETE'
    })
        .then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log(data);
                loadExamsForStudent(document.querySelector('input[name="student"]:checked').value)
            }) : console.error('Error while deleting exam: ' + response.status + ' ' + response.statusText);
        })
}

function updateExam (examId, dateOfExam, duration, subjectId) {
    document.getElementById('examId').value = examId;
    document.getElementById('examDate').valueAsDate = new Date();
    document.getElementById('examDuration').value = duration;
    document.getElementById('subjectSelection').value = subjectId;
    document.getElementById('examAction').value = 'Update Exam';

    document.getElementById('examAction').onclick = function () {
        const examId = document.getElementById('examId').value;
        const studentId = document.querySelector('input[name="student"]:checked').value;
        const subjectId = document.getElementById('subjectSelection').value;
        const date = document.getElementById('examDate').value;
        const duration = document.getElementById('examDuration').value;

        if (!(document.getElementById('examId').validity.valid &&
            document.getElementById('examDate').validity.valid &&
            document.getElementById('examDuration').validity.valid)) {
            alert('Please check your input');
            return;
        }

        const exam = {
            examId: examId,
            studentId: studentId,
            subjectId: subjectId,
            dateOfExam: date,
            duration: duration
        };

        document.getElementById('examId').value = '';
        document.getElementById('examDate').value = '';
        document.getElementById('examDuration').value = '';
        document.getElementById('examAction').value = 'Add Exam';
        document.getElementById('examAction').onclick = function () { addNewExam() };
        loadSubjects();

        fetch('./exam', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(exam)
        }).then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log('Exam updated successfully');
                console.log(data);

                loadExamsForStudent(studentId);

            }) : console.error('Error while updating exam: ' + response.status + ' ' + response.statusText);
        })
    };
}

function loadSubjects() {
    fetch('./subject/all')
        .then(response => {
            response.status === 200 ? response.json().then(data => {
                console.log(data);

                data.forEach(subject => {
                    const option = document.createElement('option');
                    option.text = subject.longname;
                    option.value = subject.subjectId;
                    document.getElementById('subjectSelection').appendChild(option);
                });
            }) : console.error('Error while loading subjects: ' + response.status + ' ' + response.statusText);
        })
}

function addNewExam() {
    const examId = document.getElementById('examId').value;
    const studentId = document.querySelector('input[name="student"]:checked').value;
    const subjectId = document.getElementById('subjectSelection').value;
    const date = document.getElementById('examDate').value;
    const duration = document.getElementById('examDuration').value;

    if (!(document.getElementById('examId').validity.valid &&
        document.getElementById('examDate').validity.valid &&
        document.getElementById('examDuration').validity.valid)) {
        alert('Please check your input');
        return;
    }

    const exam = {
        examId: examId,
        studentId: studentId,
        subjectId: subjectId,
        dateOfExam: date,
        duration: duration
    };

    document.getElementById('examId').value = '';
    document.getElementById('examDate').value = '';
    document.getElementById('examDuration').value = '';

    console.log(exam);

    fetch('./exam/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(exam)
    })
        .then(response => {
            if (response.status === 201) {
                console.log(response);
                loadExamsForStudent(studentId);
            } else {
                console.error('Error while adding new exam: ' + response.status + ' ' + response.statusText);
            }
        })
}