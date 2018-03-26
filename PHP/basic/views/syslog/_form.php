<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Syslog */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="syslog-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'userid')->textInput(['maxlength' => 10]) ?>

    <?= $form->field($model, 'beaconid')->textInput(['maxlength' => 50]) ?>

    <?= $form->field($model, 'logtime')->textInput() ?>

    <?= $form->field($model, 'triggertype')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
