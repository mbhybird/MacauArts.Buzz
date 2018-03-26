<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\BeaconSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="beacon-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'beaconid') ?>

    <?= $form->field($model, 'displayname') ?>

    <?= $form->field($model, 'major') ?>

    <?= $form->field($model, 'minor') ?>

    <?php // echo $form->field($model, 'priority') ?>

    <?php // echo $form->field($model, 'effectiverangein') ?>

    <?php // echo $form->field($model, 'effectiverangeout') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-default']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
