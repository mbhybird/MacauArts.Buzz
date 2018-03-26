<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model app\models\Sysparams */

$this->title = 'Update Sysparams: ' . ' ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Sysparams', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="sysparams-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
