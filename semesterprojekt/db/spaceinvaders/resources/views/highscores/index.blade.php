<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Laravel</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css">
    </head>
    <body>
      <div class="container">
        <h1>
          Highscore Spaceinvaders
        </h1>
        <div class="row">
          <div class="col-md-8">

            <table class="table">
              <thead>
                <th>id</th>
                <th>name</th>
                <th>punkte</th>
                <th>created_at</th>
                <th>updated_at</th>

              </thead>
              <tbody>
                @foreach($highscores as $highscore)
                <tr>
                  <td>{{ $highscore->id }}</td>
                  <td>{{ $highscore->name }}</td>
                  <td>{{ $highscore->punkte }}</td>
                  <td>{{ $highscore->created_at }}</td>
                  <td>{{ $highscore->updated_at }}</td>
                </tr>
                @endforeach

              </tbody>
            </table>
            <div class="text-center">
              {!! $highscores->links() !!}
            </div>
          </div>
      </div>

    </body>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

  </html>
