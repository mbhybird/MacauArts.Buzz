<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\Sysparams */

$this->title = 'Create Sysparams';
$this->params['breadcrumbs'][] = ['label' => 'Sysparams', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="sysparams-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
