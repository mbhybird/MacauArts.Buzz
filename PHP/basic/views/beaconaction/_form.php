<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Beaconaction */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="beaconaction-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'beaconid')->textInput(['maxlength' => 20]) ?>

    <?= $form->field($model, 'triggerid')->textInput() ?>

    <?= $form->field($model, 'contentid')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
