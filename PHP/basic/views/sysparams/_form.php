<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Sysparams */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="sysparams-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'paramgroup')->textInput(['maxlength' => 20]) ?>

    <?= $form->field($model, 'paramkey')->textInput() ?>

    <?= $form->field($model, 'paramvalue')->textInput(['maxlength' => 20]) ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
