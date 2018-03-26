<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\Syslog */

$this->title = 'Create Syslog';
$this->params['breadcrumbs'][] = ['label' => 'Syslogs', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="syslog-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
