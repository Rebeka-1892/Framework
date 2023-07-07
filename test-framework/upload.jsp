<!DOCTYPE html>
<html>
<head>
    <title>Formulaire de telechargement de fichiers</title>
</head>
<body>
    <h1>Formulaire de telechargement de fichiers</h1>
    <form action="/testfw/emp_upload.do" method="POST" enctype="multipart/form-data">
        <div>
            <label>Selectionnez un fichier :</label>
            <input type="file" name="file" required>
        </div>
        <input type="submit" value="Telecharger">
    </form>
</body>
</html>