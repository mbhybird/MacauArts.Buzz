<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model app\models\Syslog */

$this->title = 'Update Syslog: ' . ' ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Syslogs', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="syslog-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
