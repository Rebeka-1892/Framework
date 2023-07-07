<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire</title>
</head>
<body>
    <h1>Ajout employe</h1>
    <form action="/testfw/emp_save.do" method="post">
        <input type="text" name="nom" placeholder="nom">
        <input type="text" name="age" placeholder="age">
        <input type="submit" value="Valider">
    </form>
</body>
</html>