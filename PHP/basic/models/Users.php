<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "user".
 *
 * @property integer $id
 * @property string $userid
 * @property string $usernamecn
 * @property string $usernameen
 * @property integer $isauthorized
 * @property integer $defaultlang
 */
class Users extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'user';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['isauthorized', 'defaultlang'], 'integer'],
            [['userid', 'usernamecn', 'usernameen'], 'string', 'max' => 20]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'userid' => 'Userid',
            'usernamecn' => 'Usernamecn',
            'usernameen' => 'Usernameen',
            'isauthorized' => 'Isauthorized',
            'defaultlang' => 'Defaultlang',
        ];
    }
}
